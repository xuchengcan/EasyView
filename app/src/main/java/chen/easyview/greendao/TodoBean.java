package chen.easyview.greendao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by chen on 2017/2/17.
 */

@Entity
public class TodoBean {

    @Id(autoincrement = true)
    public Long ID;

    public String Name;
    public String Title;
    public String Url;
    public String Type;
    public String StartTime;
    public String StopTime;
    public String CreatTime;
    public Boolean IsComplete;

    @Override
    public String toString() {
        return "TodoBean{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Title='" + Title + '\'' +
                ", Url='" + Url + '\'' +
                ", Type='" + Type + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", StopTime='" + StopTime + '\'' +
                ", CreatTime='" + CreatTime + '\'' +
                ", IsComplete=" + IsComplete +
                '}';
    }

    @Generated(hash = 322114634)
    public TodoBean(Long ID, String Name, String Title, String Url, String Type,
            String StartTime, String StopTime, String CreatTime,
            Boolean IsComplete) {
        this.ID = ID;
        this.Name = Name;
        this.Title = Title;
        this.Url = Url;
        this.Type = Type;
        this.StartTime = StartTime;
        this.StopTime = StopTime;
        this.CreatTime = CreatTime;
        this.IsComplete = IsComplete;
    }

    @Generated(hash = 1563990781)
    public TodoBean() {
    }


    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getUrl() {
        return this.Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getType() {
        return this.Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getStartTime() {
        return this.StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getStopTime() {
        return this.StopTime;
    }

    public void setStopTime(String StopTime) {
        this.StopTime = StopTime;
    }

    public String getCreatTime() {
        return this.CreatTime;
    }

    public void setCreatTime(String CreatTime) {
        this.CreatTime = CreatTime;
    }

    public Boolean getIsComplete() {
        return this.IsComplete;
    }

    public void setIsComplete(Boolean IsComplete) {
        this.IsComplete = IsComplete;
    }

}

//@Entity：告诉GreenDao该对象为实体，只有被@Entity注释的Bean类才能被dao类操作
//@Id：对象的Id，使用Long类型作为EntityId，否则会报错。(autoincrement = true)表示主键会自增，如果false就会使用旧值
//@Property：可以自定义字段名，注意外键不能使用该属性
//@NotNull：属性不能为空
//@Transient：使用该注释的属性不会被存入数据库的字段中
//@Unique：该属性值必须在数据库中是唯一值
//@Generated：编译后自动生成的构造函数、方法等的注释，提示构造函数、方法等不能被修改