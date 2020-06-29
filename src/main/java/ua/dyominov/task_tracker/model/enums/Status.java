package ua.dyominov.task_tracker.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

public enum Status implements Serializable {
    VIEW,
    IN_PROGRESS,
    DONE
}
