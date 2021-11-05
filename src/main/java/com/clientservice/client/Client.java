package com.clientservice.client;

import com.clientservice.orders.ClientOrder;
import com.clientservice.orders.DHOrder;
import com.clientservice.orders.Order;
import com.clientservice.restaurantdata.RestaurantsData;
import lombok.SneakyThrows;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.clientservice.ClientServiceApplication.*;

public class Client extends Thread {

    private static int count = 0;

    private static final String url;

    private static final TimeUnit timeUnit;

    private static final RestTemplate restTemplate;

    private static final HttpHeaders headers;

    private RestaurantsData menus;

    private final int id = count++;

    private final ArrayList<Rating> ratings = new ArrayList<>();

    private long createdOrderTimeNS;

    @SneakyThrows
    @Override
    public void run() {

        getMenu();

        ClientOrder clientOrder = generateOrder();

        HttpEntity<ClientOrder> entity = new HttpEntity<>(clientOrder, headers);

        clientOrder = restTemplate.postForObject(url + "/order", entity, ClientOrder.class);

        waitForOrders(clientOrder);

        restTemplate.postForObject(url + "/rating", ratings, String.class);

    }

    private void getMenu() {
        try {
            menus = restTemplate.getForObject(url + "/menu", RestaurantsData.class);

        } catch (ResourceAccessException | HttpClientErrorException | HttpServerErrorException e) {

            System.out.println("Food ordering service is not responding! Exiting program...");

            System.exit(1);
        }
    }

    private ClientOrder generateOrder() {
        int numberOfOrders = (int) (Math.random() * menus.getRestaurants() + 1);

        ArrayList<Integer> restIds = new ArrayList<>();

        while (restIds.size() < numberOfOrders) {
            int randId = (int) (Math.random() * menus.getRestaurants() + 1);

            if (!restIds.contains(randId)) restIds.add(randId);
        }

        ArrayList<Order> orders = new ArrayList<>();

        for (Integer restId : restIds)
            orders.add(new Order(menus.getRestById(restId)));

        createdOrderTimeNS = System.nanoTime();

        System.out.println("Client " + id + " has generated new order!");

        return new ClientOrder(id, orders);
    }

    private void waitForOrders(ClientOrder clientOrder) throws InterruptedException {

        if (clientOrder == null) return;

        Order order;

        DHOrder dhOrder;

        while (!clientOrder.isEmpty()) {

            order = clientOrder.minWaitingTime();

            int estimatedTime = order.getEstimatedWaitingTime();

            timeUnit.sleep(estimatedTime);

            clientOrder.reduceTime(estimatedTime);

            dhOrder = restTemplate.getForObject("http://" + order.getAddress() + "/v2/order/" + order.getOrderId(), DHOrder.class);

            if (dhOrder == null) {clientOrder.remove(order); continue;}

            if (dhOrder.isReady()) { clientOrder.remove(order); rateOrder(order); }

            order.setEstimatedWaitingTime(dhOrder.getEstimatedWaitingTime());

        }
    }

    private void rateOrder(Order order) {
        int rating = 0;

        long now = timeUnit.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

        long createdOrderTime = timeUnit.convert(createdOrderTimeNS, TimeUnit.NANOSECONDS);

        System.out.println(now + " " + createdOrderTime);

        if (now <= createdOrderTime * 1.1) rating = 5; else
        if (now <= createdOrderTime * 1.2) rating = 4; else
        if (now <= createdOrderTime * 1.3) rating = 3; else
        if (now <= createdOrderTime * 1.4) rating = 2; else
        if (now <= createdOrderTime * 1.5) rating = 1;

        System.out.println("Client " + id + "has received order and rated " + rating + "*!");

        ratings.add(new Rating(order.getRestaurantId(), rating));

    }

    static {
        url = getUrl();

        timeUnit = getTimeUnit();

        restTemplate = new RestTemplateBuilder().build();

        headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
    }
}
