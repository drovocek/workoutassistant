package ru.soft.common.data.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonRootName("roundSchema")
public record WorkoutRoundSchemaSnapshot(
        @JsonProperty("roundStations") @NotNull @Nonnull List<WorkoutStationSnapshot> roundStations) {
}
