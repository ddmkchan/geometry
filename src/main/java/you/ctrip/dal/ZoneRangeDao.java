package you.ctrip.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import you.ctrip.entity.ZoneRange;
import com.ctrip.platform.dal.dao.DalClient;
import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.DalParser;
import com.ctrip.platform.dal.dao.StatementParameters;
import com.ctrip.platform.dal.dao.helper.AbstractDalParser;
import com.ctrip.platform.dal.dao.helper.DalRowMapperExtractor;

public class ZoneRangeDao {

	private static final String DATA_BASE = "GSPOIDB_SELECT_1";
	private static final Logger logger = LoggerFactory.getLogger(ZoneRangeDao.class);

	private DalRowMapperExtractor<ZoneRange> rowextractor = null;
	private DalClient baseClient;
	
	private static ZoneRangeDao zoneRangeDao = null;
	
	private ZoneRangeDao() {
		DalParser<ZoneRange> parser = new ZoneRangeParser();
		this.rowextractor = new DalRowMapperExtractor<ZoneRange>(parser);
		this.baseClient = DalClientFactory.getClient(DATA_BASE);
	}
	
	public static ZoneRangeDao getInstance() {
		if(zoneRangeDao == null) {
			zoneRangeDao = new ZoneRangeDao();
		}
		return zoneRangeDao;
	}
	
	public List<ZoneRange> getZoneRangeList() {
		StringBuilder strSqls = new StringBuilder();
		strSqls.append("SELECT Zone AS zone, PointLon AS pointLon, PointLat AS pointLat ");
		strSqls.append("FROM ZoneRange(nolock) ");
		strSqls.append("WHERE PointType=1 ");
		StatementParameters parameters = new StatementParameters();
		DalHints hints = new DalHints();
		List<ZoneRange> list = null;
		try {
			list = baseClient.query(strSqls.toString(), parameters, hints,
					rowextractor);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		return list;
	}
	
	public static class ZoneRangeParser extends AbstractDalParser<ZoneRange> {
		public static final String DATABASE_NAME = "GSPOIDB_SELECT_1";
		public static final String TABLE_NAME = "ZoneRange";
		private static final String[] COLUMNS = new String[] { "zone",
				"pointLon", "pointLat" };

		private static final String[] PRIMARY_KEYS = new String[] { "ZoneRangeId" };

		private static final int[] COLUMN_TYPES = new int[] { Types.BIGINT,
				Types.FLOAT, Types.FLOAT, Types.BIGINT };

		public ZoneRangeParser() {
			super(DATABASE_NAME, TABLE_NAME, COLUMNS, PRIMARY_KEYS,
					COLUMN_TYPES);
		}

		@Override
		public ZoneRange map(ResultSet rs, int rowNum) throws SQLException {
			ZoneRange pojo = new ZoneRange();
			pojo.setZone(rs.getInt("zone"));
			pojo.setPointLon(rs.getFloat("pointLon"));
			pojo.setPointLat(rs.getFloat("pointLat"));
			return pojo;
		}

		@Override
		public boolean isAutoIncrement() {
			return false;
		}

		@Override
		public Number getIdentityValue(ZoneRange pojo) {
			return null;
		}

		@Override
		public Map<String, ?> getPrimaryKeys(ZoneRange pojo) {
			Map<String, Object> primaryKeys = new LinkedHashMap<String, Object>();

			return primaryKeys;
		}

		@Override
		public Map<String, ?> getFields(ZoneRange pojo) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("zone", pojo.getZone());
			map.put("pointLon", pojo.getPointLon());
			map.put("pointLat", pojo.getPointLat());
			return map;
		}
	}
}
