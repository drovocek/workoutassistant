package ru.soft.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import ru.soft.common.data.snapshot.WorkoutSnapshot;

import java.util.List;

@JsonRootName("workoutsSchema")
public record WorkoutsSchema(
        @JsonProperty("workoutsSchema") @Nonnull @NotNull List<WorkoutSnapshot> workoutsSchema) {
}
