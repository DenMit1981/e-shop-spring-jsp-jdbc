package service.impl;

import dao.GoodDAO;
import model.Good;
import org.springframework.stereotype.Service;
import service.GoodService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodServiceImpl implements GoodService {

    private static final String REGEX_LETTERS_FIGURES_POINT = "[^A-Za-z0-9.]";
    private static final String ORDER_NOT_MADE = "Make your order\n";

    private final GoodDAO goodDAO;

    public GoodServiceImpl(GoodDAO goodDAO) {
        this.goodDAO = goodDAO;
    }

    @Override
    public List<Good> getAll() {
        return goodDAO.getAll();
    }

    @Override
    public String getStringOfOptionsForDroppingMenuFromGoodList(List<Good> goods) {
        return goods.stream()
                .map(good -> "<option>" + good.getTitle() + " (" + good.getPrice() + ") </option>\n")
                .collect(Collectors.joining());
    }

    @Override
    public String getChoice(String chosenGoods) {
        if (chosenGoods != null) {
            return chosenGoods;
        }

        return ORDER_NOT_MADE;
    }

    @Override
    public String getStringOfNameAndPriceFromOptionMenu(String menu) {
        return menu.replaceAll(REGEX_LETTERS_FIGURES_POINT, "");
    }
}
