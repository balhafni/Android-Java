/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jeromy
 */
public class WagesCalculator {
	int hour;
	double grossPay;
	double Fedincometax;
	double Medicare;
	double SocialSecurity;
	double NYSincometax;
	double NYCincometax;
	double nettakehomepay;
	public final int uniondue = 10;

	WagesCalculator(int hour) {
		this.hour = hour;
	}

	public int getHours() {
		return this.hour;
	}

	public double getgrossPay() {
		return this.hour * 23.67;
	}

	public double getFedincometax() {
		return getgrossPay() * 0.1218;
	}

	public double getMedicare() {
		return getgrossPay() * 0.0145;
	}

	public double getSocialSecurity() {
		return getgrossPay() * 0.042;
	}

	public double getNYSincometax() {
		return getgrossPay() * 0.0355;
	}

	public double getNYCincometax() {
		return getgrossPay() * 0.0228;
	}

	public double getNettakehomepay() {
		return getgrossPay() - getFedincometax() - getMedicare() - getSocialSecurity() - getNYSincometax()
				- getNYCincometax() - uniondue;
	}
}
