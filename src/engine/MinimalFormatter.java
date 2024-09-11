package engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Implements a simple logging format.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class MinimalFormatter extends Formatter {

	/** Format for the date.
	 * 시간을 형식에 맞추어 저장
	 * DateFormat: 추상클래스, SimpleDateFormat가 상속
	 */
	private static final DateFormat FORMAT = new SimpleDateFormat("h:mm:ss");

	/** System line separator.
	 * 개행 문자를 불러와 저장
	 */
	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	/** 레벨과 시간과 파라미터를 형식에 맞춰 문자열로 반환 */
	@Override
	public final String format(final LogRecord logRecord) {

		StringBuilder output = new StringBuilder().append("[")
				.append(logRecord.getLevel()).append('|')
				.append(FORMAT.format(new Date(logRecord.getMillis())))
				.append("]: ").append(logRecord.getMessage()).append(' ')
				.append(LINE_SEPARATOR);

		return output.toString();
	}
}
