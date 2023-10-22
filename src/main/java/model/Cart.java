package model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Cart {

    private final List<Good> goods;

    private User user;

    public Cart() {
        this.goods = new ArrayList();
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addGood(Good good) {
        goods.add(good);
    }

    public void deleteGood(Good good) {
        goods.remove(good);
    }

    public void deleteGoods() {
        goods.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(goods, cart.goods) && Objects.equals(user, cart.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods, user);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "goods=" + goods +
                ", user=" + user +
                '}';
    }
}
