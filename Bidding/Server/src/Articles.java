import java.io.Serializable;

public class Articles implements Serializable {
    private static final long serialVersionUID = 706700525844567291L;
    private Long id;
    private int status;
    private String category;
    private String name;
    private String model;
    private double price;
    private String description;
    private String time;
    private String dateOfCreate;

    public Articles(Long id, int status, String category, String name, String model, double price, String description, String time, String dateOfCreate) {
        this.id=id;
        this.status=status;
        this.category=category;
        this.name = name;
        this.model = model;
        this.price = price;
        this.description = description;
        this.time = time;
        this.dateOfCreate=dateOfCreate;
    }
    public Articles(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateOfCreate() {
        return dateOfCreate;
    }

    public void setDateOfCreate(String dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }
}
