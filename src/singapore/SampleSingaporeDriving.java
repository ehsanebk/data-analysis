package singapore;

import analysis.Utilities;

public class SampleSingaporeDriving {
	double Video;
	double Time;
	double LeftRho;
	double LeftTheta;
	double RightRho;
	double RightTheta;
	double LeftX;
	double RightX;
	double LaneWidth;
	double LaneCenter;
	double LateralPosition;
	double LeftRhoThetaOK;
	double RightRhoThetaOK;
	double LeftSigSpikeOK;
	double RightSigSpikeOK;
	double LaneWidthOK;
	double isDriving; 

	public String toString() {
		return Utilities.df3.format(Video) + "\t" +
				Utilities.df3.format(Time) + "\t" +
				Utilities.df3.format(LeftRho) + "\t" +
				Utilities.df3.format(LeftTheta) + "\t" +
				Utilities.df3.format(RightRho) + "\t" +
				Utilities.df3.format(RightTheta) + "\t" +
				Utilities.df3.format(LeftX) + "\t" +
				Utilities.df3.format(RightX) + "\t" +
				Utilities.df3.format(LaneWidth) + "\t" +
				Utilities.df3.format(LaneCenter) + "\t" +
				Utilities.df3.format(LateralPosition) + "\t" +
				Utilities.df3.format(LeftRhoThetaOK) + "\t" +
				Utilities.df3.format(RightRhoThetaOK) + "\t" +
				Utilities.df3.format(LeftSigSpikeOK) + "\t" +
				Utilities.df3.format(RightSigSpikeOK) + "\t" +
				Utilities.df3.format(LaneWidthOK) + "\t" +
				Utilities.df3.format(isDriving);
	}
}
