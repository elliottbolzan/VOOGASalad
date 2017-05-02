package utils.math;

public class IntChecker {
	public boolean check(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
