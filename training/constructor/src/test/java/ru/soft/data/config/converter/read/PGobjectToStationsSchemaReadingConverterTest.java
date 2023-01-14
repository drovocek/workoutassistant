package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.StationsSchema;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.stationsSchema;

class PGobjectToStationsSchemaReadingConverterTest extends BasePGobjectToEntityReadingConverterTest<StationsSchema, PGobjectToStationSchemaReadingConverter> {

    @Override
    protected PGobjectToStationSchemaReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToStationSchemaReadingConverter(mapper);
    }

    @Override
    protected StationsSchema expected() {
        return stationsSchema();
    }
}