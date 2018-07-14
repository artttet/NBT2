package chiglintsev.notboringtrails20.models;

public class Routes {
    public String title;
    public int img;
    int id;

    public Routes(int id, String title, int img) {
        this.id = id;
        this.title = title;
        this.img = img;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public int getImg() {
        return this.img;
    }

}
