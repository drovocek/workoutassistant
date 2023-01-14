package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.StationsSchema;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.stationsSchema;

class StationsSchemaToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<StationsSchema, StationSchemaToPGobjectWritingConverter> {

    @Override
    protected StationSchemaToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new StationSchemaToPGobjectWritingConverter(mapper);
    }

    @Override
    protected StationsSchema forWriting() {
        return stationsSchema();
    }
}