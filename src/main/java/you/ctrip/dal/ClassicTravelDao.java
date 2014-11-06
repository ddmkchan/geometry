package you.ctrip.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import you.ctrip.entity.Travelnote;

import com.ctrip.platform.dal.dao.DalClient;
import com.ctrip.platform.dal.dao.DalClientFactory;
import com.ctrip.platform.dal.dao.DalHints;
import com.ctrip.platform.dal.dao.StatementParameters;
import com.ctrip.platform.dal.dao.helper.AbstractDalParser;
import com.ctrip.platform.dal.dao.helper.DalRowMapperExtractor;


public class ClassicTravelDao {

	private static final String DATA_BASE = "GSTravelsDB_SELECT_1";
	private static final Logger logger = LoggerFactory.getLogger(ClassicTravelDao.class);

	private DalRowMapperExtractor<Travelnote> rowextractor = null;
	private DalClient baseClient;
	
	private static ClassicTravelDao classicTravelDao = null;

	private ClassicTravelDao() {
//		this.client = new DalTableDao<Travelnote>(parser);
		this.rowextractor = new DalRowMapperExtractor<Travelnote>(new ClassicTravelParser());
		this.baseClient = DalClientFactory.getClient(DATA_BASE);
	}
	
	public static ClassicTravelDao getInstance() {
		if(classicTravelDao == null) {
			classicTravelDao = new ClassicTravelDao();
		}
		return classicTravelDao;
	}

	public List<Travelnote> getClassicTravelList() {
		StringBuilder strSqls = new StringBuilder();
		strSqls.append("SELECT t.TravelId AS travelId, t.TravelTitle AS title, ");
		strSqls.append("ct.TravelContent AS content, t.PublishDate AS publishDate ");
		strSqls.append("FROM Travel(nolock) t ");
		strSqls.append("INNER JOIN dbo.ClassicTravel(nolock) ct ON t.TravelId=ct.TravelId ");
		strSqls.append("WHERE t.TravelType=1 AND t.TravelStatus=1 AND t.TravelOperationStatus>=100 ");
		StatementParameters parameters = new StatementParameters();
		DalHints hints = new DalHints();
		List<Travelnote> list = null;
		try {
			list = baseClient.query(strSqls.toString(), parameters, hints,
					rowextractor);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		return list;
	}

	public Travelnote getClassicTravelByTravelId(long travelId) {
		StringBuilder strSqls = new StringBuilder();
		strSqls.append("SELECT t.TravelId AS travelId, t.TravelTitle AS title, t.PublishDate AS publishDate, ");
		strSqls.append("ct.TravelContent AS content ");
		strSqls.append("FROM Travel(nolock) t  ");
		strSqls.append("INNER JOIN ClassicTravel(nolock) ct ON t.TravelId=ct.TravelId ");
		strSqls.append("WHERE t.TravelId =? AND t.TravelType=1 AND t.TravelStatus != 2 ");
		StatementParameters parameters = new StatementParameters();
		parameters.set(1, Types.BIGINT, travelId);
		DalHints hints = new DalHints();
		List<Travelnote> list = null;
		Travelnote entity = null;
		try {
			list = baseClient.query(strSqls.toString(), parameters, hints,
					rowextractor);
			if (!CollectionUtils.isEmpty(list)) {
				entity = list.get(0);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
		return entity;
	}

	public static class ClassicTravelParser extends AbstractDalParser<Travelnote> {
		public static final String DATABASE_NAME = "GSTravelsDB_SELECT_1";
		public static final String TABLE_NAME = "Travel";
		private static final String[] COLUMNS = new String[] { "travelId",
				"title", "content", "publishDate" };

		private static final String[] PRIMARY_KEYS = new String[] { "travelId" };

		private static final int[] COLUMN_TYPES = new int[] { Types.BIGINT,
				Types.NVARCHAR, Types.NVARCHAR, Types.NVARCHAR, Types.BIGINT };

		public ClassicTravelParser() {
			super(DATABASE_NAME, TABLE_NAME, COLUMNS, PRIMARY_KEYS,
					COLUMN_TYPES);
		}

		@Override
		public Travelnote map(ResultSet rs, int rowNum) throws SQLException {
			Travelnote pojo = new Travelnote();
			pojo.setTravelId(rs.getInt("travelId"));
			pojo.setTitle(rs.getString("title"));
			pojo.setContent(rs.getString("content"));
			pojo.setPublishDate(rs.getString("publishDate"));
			return pojo;
		}

		@Override
		public boolean isAutoIncrement() {
			return false;
		}

		@Override
		public Number getIdentityValue(Travelnote pojo) {
			return null;
		}

		@Override
		public Map<String, ?> getPrimaryKeys(Travelnote pojo) {
			Map<String, Object> primaryKeys = new LinkedHashMap<String, Object>();

			return primaryKeys;
		}

		@Override
		public Map<String, ?> getFields(Travelnote pojo) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();

			map.put("travelId", pojo.getTravelId());
			map.put("title", pojo.getTitle());
			map.put("content", pojo.getContent());
			map.put("publishDate", pojo.getPublishDate());

			return map;
		}
	}
}
