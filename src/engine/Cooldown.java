package engine;

/**
 * Imposes a cooldown period between two actions.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Cooldown {

	/** 쿨타임 */
	private int milliseconds;
	/** 쿨타임들 간의 최대 시간 차이. */
	private int variance;
	/** 이번 쿨타임 시간, variance가 0보다 크면 그 변화에 맞게 무작위로 주어짐. */
	private int duration;
	/** 쿨다운 시작시간. */
	private long time;

	/**
	 * 생성자, 다시 해당 작업을 할 수 있을때까지의 시간을 설립.
	 *
	 * 
	 * @param milliseconds
	 *            쿨타임이 끝나기 까지의 시간
	 */
	protected Cooldown(final int milliseconds) {
		this.milliseconds = milliseconds;
		this.variance = 0;
		this.duration = milliseconds;
		this.time = 0;
	}

	/**
	 * 생성자, 쿨타임 시간과 쿨타임을 랜덤으로 변경해주는 +/-값을 가진 변동을 같이 가짐.
	 *
	 * 
	 * @param milliseconds
	 *            쿨타임이 끝나기 까지의 시간
	 * @param variance
	 *            쿨타임에의 변화를 부여함
	 */
	protected Cooldown(final int milliseconds, final int variance) {
		this.milliseconds = milliseconds;
		this.variance = variance;
		this.time = 0;
	}

	/**
	 * 쿨다운이 끝난는지를 확인하는 메소드
	 * 
	 * @return 쿨타임이 다 돌았는지(true), 안돌았는지(false) 반환
	 */
	public final boolean checkFinished() {
		if ((this.time == 0)
				|| this.time + this.duration < System.currentTimeMillis())
			return true;
		return false;
	}

	/**
	 * 쿨다운 재시작하는 메소드
	 */
	public final void reset() {
		this.time = System.currentTimeMillis(); //현재 시간을 밀리초 단위로 time에 저장
		if (this.variance != 0) //variance가 0이 아니면 duration을 무작위로 설정
			this.duration = (this.milliseconds - this.variance) //최소 쿨타임
					+ (int) (Math.random()
							* (this.milliseconds + this.variance)); //0이상 최대쿨타임 미만의 시간을 더함
	}
}
