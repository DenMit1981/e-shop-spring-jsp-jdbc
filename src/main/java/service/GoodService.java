package service;

import model.Good;

import java.util.List;

public interface GoodService {

    List<Good> getAll();

    String getStringOfOptionsForDroppingMenuFromGoodList(List<Good> goods);

    String getChoice(String chosenGoods);

    String getStringOfNameAndPriceFromOptionMenu(String menu);
}
