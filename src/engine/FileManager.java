package engine;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import engine.DrawManager.SpriteType;

/**
 * Manages files used in the application.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class FileManager {

	/** 이 클래스의 싱글톤 객체. */
	private static FileManager instance;
	/** Application logger. */
	private static Logger logger;
	/** 최고 점수의 최대 개수. */
	private static final int MAX_SCORES = 7;

	/**
	 * private constructor.
	 */
	private FileManager() {
		logger = Core.getLogger();
	}

	/**
	 * 하나의 싱글톤 파일매니저의 객체를 사용.
	 * 
	 * @return Shared instance of FileManager.
	 */
	protected static FileManager getInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	/**
	 * 디스크에서 스프라이트 데이터 로드.
	 * 
	 * @param spriteMap
	 *            Mapping of sprite type and empty boolean matrix that will
	 *            contain the image.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public void loadSprite(final Map<SpriteType, boolean[][]> spriteMap)
			throws IOException {
		InputStream inputStream = null; //데이터를 읽기 위한 InputStream 사용, null로 초기화

		try {
			inputStream = DrawManager.class.getClassLoader()
					.getResourceAsStream("graphics"); //리소스 파일에서 graphics 이름을 가진 리소스 읽기
			char c; //읽어온 데이터를 저장할 변수

			// Sprite loading.
			for (Map.Entry<SpriteType, boolean[][]> sprite : spriteMap
					.entrySet()) { //Map의 형태로 (키, boolean[][]의 값)으로 엔트리를 저장
				for (int i = 0; i < sprite.getValue().length; i++)
					for (int j = 0; j < sprite.getValue()[i].length; j++) { // boolean[][]의 각 행의 열을 순회
						do
							c = (char) inputStream.read();  //C에 읽은 데이터를 저장
						while (c != '0' && c != '1'); //c가 0과 1이 아닐때 까지 반복

						if (c == '1') //c가 1이 되면
							sprite.getValue()[i][j] = true; //읽은 데이터의 boolean의 해당 위치를 true로 변경
						else
							sprite.getValue()[i][j] = false; //읽은 데이터의 boolean의 해당 위치를 false로 변경
					}
				logger.fine("Sprite " + sprite.getKey() + " loaded."); //스프라이트 로드 메세지 출력
			}
		} finally {
			if (inputStream != null) //데이터 읽기 종료
				inputStream.close();
		}
	}

	/**
	 * 폰트를 주어진 사이즈로 로드
	 * 
	 * @param size
	 *            폰트의 사이즈
	 * @return New font.
	 * @throws IOException
	 *             In case of loading problems.
	 * @throws FontFormatException
	 *             In case of incorrect font format.
	 */
	public Font loadFont(final float size) throws IOException,
			FontFormatException {
		InputStream inputStream = null; //inputStream으로 읽기
		Font font;

		try {
			// Font loading.
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("font.ttf"); //클래스로더로 font.ttf파일 읽기
			font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(
					size); //truetype 폰트 지정, 폰트 사이즈 지정, 폰트 객체 반환
		} finally {
			if (inputStream != null) // 읽기 종료
				inputStream.close();
		}

		return font; //읽어온 폰트 반환
	}

	/**
	 * 유저의 최대 점수가 없는 경우, 어플리케이션의 기본 최대 점수를 반환
	 *
	 * 
	 * @return 기본 최고 점수들
	 * @throws IOException
	 *             In case of loading problems.
	 */
	private List<Score> loadDefaultHighScores() throws IOException {
		List<Score> highScores = new ArrayList<Score>(); //Score객체 저장할 highscores 배열 선언
		InputStream inputStream = null; // 데이터를 읽기 위해 inputstream 준비
		BufferedReader reader = null;

		try {
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("scores");
			reader = new BufferedReader(new InputStreamReader(inputStream)); //scores 파일에서 데이터 읽어옴

			Score highScore = null;
			String name = reader.readLine();
			String score = reader.readLine(); //첫 두 줄을 읽고 각각 name과 score에 저장

			while ((name != null) && (score != null)) { //name과 score가 null이 아니면 반복
				highScore = new Score(name, Integer.parseInt(score)); //읽어온 name과 score로 Score 객체 생성, score 정수로 변환
				highScores.add(highScore); //생성한 score객체를 처음 만든 highscores 배열에 저장
				name = reader.readLine();
				score = reader.readLine(); //다시 다음 두 줄을 읽고 name과 score에 저장
			}
		} finally {
			if (inputStream != null) // 데이터 읽기 종료
				inputStream.close();
		}

		return highScores; // scores 파일을 읽고 name과 scores(정수)를 가진 score 객체를 원소로 하는 highScores 배열 반환
	}

	/**
	 * 최고점수를 파일로부터 로드하고, 스코어 점수에 따라 정렬
	 *
	 * 
	 * @return Sorted list of scores - players.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public List<Score> loadHighScores() throws IOException {

		List<Score> highScores = new ArrayList<Score>(); //배열 선언
		InputStream inputStream = null;
		BufferedReader bufferedReader = null; . //데이터 읽을 준비

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);
			inputStream = new FileInputStream(scoresFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			logger.info("Loading user high scores.");

			Score highScore = null;
			String name = bufferedReader.readLine();
			String score = bufferedReader.readLine();

			while ((name != null) && (score != null)) {
				highScore = new Score(name, Integer.parseInt(score));
				highScores.add(highScore);
				name = bufferedReader.readLine();
				score = bufferedReader.readLine();
			}

		} catch (FileNotFoundException e) {
			// loads default if there's no user scores.
			logger.info("Loading default high scores.");
			highScores = loadDefaultHighScores();
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		Collections.sort(highScores);
		return highScores;
	}

	/**
	 * Saves user high scores to disk.
	 * 
	 * @param highScores
	 *            High scores to save.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public void saveHighScores(final List<Score> highScores) 
			throws IOException {
		OutputStream outputStream = null;
		BufferedWriter bufferedWriter = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);

			if (!scoresFile.exists())
				scoresFile.createNewFile();

			outputStream = new FileOutputStream(scoresFile);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					outputStream, Charset.forName("UTF-8")));

			logger.info("Saving user high scores.");

			// Saves 7 or less scores.
			int savedCount = 0;
			for (Score score : highScores) {
				if (savedCount >= MAX_SCORES)
					break;
				bufferedWriter.write(score.getName());
				bufferedWriter.newLine();
				bufferedWriter.write(Integer.toString(score.getScore()));
				bufferedWriter.newLine();
				savedCount++;
			}

		} finally {
			if (bufferedWriter != null)
				bufferedWriter.close();
		}
	}
}
