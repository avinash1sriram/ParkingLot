package fileio.parkinglot.model;

// This entity/model holds all the driver details
public class DriverDetails {

    private Long age;

    public DriverDetails( Long age) {
        this.age = age;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

}
