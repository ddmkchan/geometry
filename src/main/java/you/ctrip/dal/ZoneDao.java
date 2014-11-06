package you.ctrip.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import you.ctrip.entity.ZoneEntity;

import com.ctrip.platform.dal.dao.DalClient;
import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalParser;
import com.ctrip.platform.dal.dao.StatementParameters;
import com.ctrip.platform.dal.dao.helper.AbstractDalParser;
import com.ctrip.platform.dal.dao.helper.DalRowMapperExtractor;

public class ZoneDao {

	private static final String DATA_BASE = "GSPOIDB_SELECT_1";
	private static final Logger logger = LoggerFactory.getLogger(ZoneDao.class);

	private DalRowMapperExtractor<ZoneEntity> rowextractor = null;
	private DalClient baseClient;
	
	private static ZoneDao zoneDao = null;
	
	private ZoneDao() {
		DalParser<ZoneEntity> parser = new ZoneParser();
		this.rowextractor = new DalRowMapperExtractor<ZoneEntity>(parser);
		this.baseClient = DalClientFactory.getClient(DATA_BASE);
	}
	
	public static ZoneDao getInstance() {
		if(zoneDao == null) {
			zoneDao = new ZoneDao();
		}
		return zoneDao;
	}
	
	public List<ZoneEntity> getZoneList() {
		StringBuilder strSqls = new StringBuilder();
		strSqls.append("SELECT Zone AS zone, ZoneName AS zoneName ");
		strSqls.append("FROM Zone(nolock) ");
		StatementParameters parameters = new StatementParameters();
		DalHints hints = new DalHints();
		List<ZoneEntity> list = null;
		try {
			list = baseClient.query(strSqls.toString(), parameters, hints,
					rowextractor);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		return list;
	}
	
	public static class ZoneParser extends AbstractDalParser<ZoneEntity> {
		public static final String DATABASE_NAME = "GSPOIDB_SELECT_1";
		public static final String TABLE_NAME = "Zone";
		private static final String[] COLUMNS = new String[] { "zone",
				"zoneName" };

		private static final String[] PRIMARY_KEYS = new String[] { "Zone" };

		private static final int[] COLUMN_TYPES = new int[] { Types.BIGINT,
				Types.VARCHAR, Types.BIGINT };

		public ZoneParser() {
			super(DATABASE_NAME, TABLE_NAME, COLUMNS, PRIMARY_KEYS,
					COLUMN_TYPES);
		}

		@Override
		public ZoneEntity map(ResultSet rs, int rowNum) throws SQLException {
			ZoneEntity pojo = new ZoneEntity();
			pojo.setZone(rs.getInt("zone"));
			pojo.setZoneName(rs.getString("zoneName"));
			return pojo;
		}

		@Override
		public boolean isAutoIncrement() {
			return false;
		}

		@Override
		public Number getIdentityValue(ZoneEntity pojo) {
			return null;
		}

		@Override
		public Map<String, ?> getPrimaryKeys(ZoneEntity pojo) {
			Map<String, Object> primaryKeys = new LinkedHashMap<String, Object>();

			return primaryKeys;
		}

		@Override
		public Map<String, ?> getFields(ZoneEntity pojo) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("zone", pojo.getZone());
			map.put("zoneName", pojo.getZoneName());
			return map;
		}
	}
}
