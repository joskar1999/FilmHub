package main.java.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Distributor {

    private int id;
    private String name;
    private Contract contract;

    public Distributor(int id) {
        createFromJSON(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    /**
     * Creating random distributor, data chosen from fake file
     *
     * @param id random value between 0-99
     */
    private void createFromJSON(int id) {
        JSONArray array = Utils.readJSONArray("\\src\\main\\resources\\json\\fakeDistributors.json");
        JSONObject distributor = (JSONObject) array.get(id);

        this.id = id;
        this.name = (String) distributor.get("name");
    }
}
