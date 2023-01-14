package ru.soft.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import ru.soft.common.data.snapshot.RoundSnapshot;

import java.util.List;

@JsonRootName("roundsSchema")
public record RoundsSchema(
        @JsonProperty("roundSnapshots") @Nonnull @NotNull List<RoundSnapshot> roundSnapshots) {
}
