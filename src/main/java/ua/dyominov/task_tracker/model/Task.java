package ua.dyominov.task_tracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import io.swagger.annotations.ApiModelProperty;
import ua.dyominov.task_tracker.model.enums.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "task")
public class Task implements Serializable {

    @Id
    @Column(name = "id", length = 30)
    @ApiModelProperty(value = "task unique identifier")
    private Integer id;

    @Column(name = "title", length = 200)
    @ApiModelProperty(value = "task title")
    private String title;

    @Column(name = "description", length = 200)
    @ApiModelProperty(value = "task description")
    private String description;

    @Column(name = "status", length = 200)
    @Enumerated(EnumType.STRING)
    @ApiModelProperty(value = "task status")
    private Status status;

    @Column(name = "user_id")
    @JsonProperty(value = "user_id")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @ApiModelProperty(value = "user assignment to task")
    private Integer userId;

    public Task() {
    }

    public Task(Integer id, String title, String description, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                status == task.status &&
                Objects.equals(userId, task.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, userId);
    }
}
