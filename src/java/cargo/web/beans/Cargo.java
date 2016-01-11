package cargo.web.beans;

/**
 *
 * @author Roman
 */
import java.io.Serializable;

public class Cargo implements Serializable {
    private long id;
    private String name;
    private byte[] content;
    private int tripCount;
    private Integer tripNumber;
    private int ctypeId;
    private int carId;
    private int dateYear;
    private int companyId;
    private String desc;
    private String short_des;

    private byte[] image;
    private String cars;
    private String company;
    private String cargo_type;

    public String getCars() {
        return cars;
    }

    public String getCompany() {
        return company;
    }
    
    public String getShort_desc() {
        return short_des;
    }

    public void setShort_desc(String short_desc) {
        this.short_des = short_desc;
    }

    public String getCargo_type() {
        return cargo_type;
    }

    public void setCars(String cars) {
        this.cars = cars;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCargo_type(String cargo_type) {
        this.cargo_type = cargo_type;
    }
    
     public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getimage() {
        return image;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }

    public void setTripNumber(Integer tripNumber) {
        this.tripNumber = tripNumber;
    }

    public void setCtypeId(int ctypeId) {
        this.ctypeId = ctypeId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getContent() {
        return content;
    }

    public int getTripCount() {
        return tripCount;
    }

    public Integer getTripNumber() {
        return tripNumber;
    }

    public int getCtypeId() {
        return ctypeId;
    }

    public int getCarId() {
        return carId;
    }

    public int getDateYear() {
        return dateYear;
    }

    public int getCompanyId() {
        return companyId;
    }

    public byte[] getImage() {
        return image;
    }  
}
