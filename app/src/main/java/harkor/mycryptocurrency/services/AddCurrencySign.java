package harkor.mycryptocurrency.services;

public  class AddCurrencySign {
    public static String addCurrencySign(int currencyTag, String amount){
        String result;
        switch (currencyTag){
            case 1: result="$"+amount;break;
            case 2: result=amount+"€";break;
            case 3: result=amount+"zł";break;
            default: result=amount+" BTC";
        }
        return result;
    }
}
