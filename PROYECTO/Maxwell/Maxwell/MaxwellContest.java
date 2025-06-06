package Maxwell;
import java.util.*;
/**
 * MaxwellContest.
 * 
 * @author (Juan Sebastian Puentes Julio y Julian Camilo Lopez Barrero y IA) 
 * @version (4/03/2025)
 */
public class MaxwellContest {
    static final double EPS = 1e-9;
    static final int MAX_ITER = 10000;
    static final double INF = 1e9; 

    static double reflect(double yPosition, double heightChamber) {
        double mod = yPosition % (2 * heightChamber);
        if (mod < 0) {
            mod += 2 * heightChamber;
        }
        if (mod <= heightChamber + EPS) {
            return mod;
        } else {
            return 2 * heightChamber - mod;
        }
    }

    static double minTime(double px, double py, double vx, double vy, double w, double h, double d) {
        double best = INF;
        if (Math.abs(vx) > EPS) {
            int kStart;
            int step;
            if (vx > 0) {
                kStart = (int) Math.ceil(px / (2 * w));
                step = 1;
            } else {
                kStart = (int) Math.floor(px / (2 * w));
                step = -1;
            }
            boolean found = false;
            for (int k = kStart; Math.abs(k - kStart) < MAX_ITER && !found; k += step) {
                double t = (2 * w * k - px) / vx;
                if (t >= 0) {
                    double y;
                    if (Math.abs(vy) < EPS) {
                        y = py;
                    } else {
                        y = reflect(py + vy * t, h);
                    }
                    if (Math.abs(y - d) < EPS) {
                        best = Math.min(best, t);
                        found = true;
                    }
                }
            }
        }
        return best;
    }

    public static String solve(int w, int h, double d, int r, int b, int[][] particles) {
        int totalParticles = r + b;
        double timer = 0.0;
        boolean possible = true;
        for (int i = 0; i < totalParticles && possible; i++) {
            double px = particles[i][0], py = particles[i][1];
            double vx = particles[i][2], vy = particles[i][3];
            boolean inLeft;
            if (px < 0) {
                inLeft = true;
            } else {
                inLeft = false;
            }
            boolean needFlip;
            if ((i < r && !inLeft) || (i >= r && inLeft)) {
                needFlip = true;
            } else {
                needFlip = false;
            }
            if (needFlip) {
                double tCandidate = minTime(px, py, vx, vy, w, h, d);
                if (tCandidate == INF) {
                    possible = false;
                } else {
                    timer = Math.max(timer, tCandidate);
                }
            }
        }
        if (possible) {
            return String.format("%.6f", timer);
        } else {
            return "impossible";
        }
    }
    
    /**
     * Method To Simulate The Maxwell Container Problem
     * @param h height of the Chamber
     * @param w width of the Chamber
     * @param d demons
     * @b particles blue
     * @r particles red 
     * @particles the particles we want with his position and velocity
     */
    public void simulate(int h, int w, int d, int b, int r, int[][] particles) {
        MaxwellContainer container = new MaxwellContainer(h, w, d, b, r, particles);
        container.makeVisible();
        container.start(1000);
    }
}