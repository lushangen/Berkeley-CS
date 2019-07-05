public class Body {
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;
  private static final double G = 6.67e-11;
  public Body(double xP, double yP, double xV, double yV, double m, String img) {
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }
  public Body(Body b) {
    xxPos = b.xxPos;
    yyPos = b.yyPos;
    xxVel = b.xxVel;
    yyVel = b.yyVel;
    mass = b.mass;
    imgFileName = b.imgFileName;
  }
  public double calcDistance(Body b) {
    double dx = (b.xxPos - xxPos);
    double dy = (b.yyPos - yyPos);
    return Math.pow((dx * dx + dy * dy), .5);
  }
  public double calcForceExertedBy(Body b) {
    double r = this.calcDistance(b);
    return (G * b.mass * mass)/(r * r);
  }
  public double calcForceExertedByX(Body b) {
    double r = this.calcDistance(b);
    double F = this.calcForceExertedBy(b);
    double dx = (b.xxPos - xxPos);
    return (F * dx / r);
  }
  public double calcForceExertedByY(Body b) {
    double r = this.calcDistance(b);
    double F = this.calcForceExertedBy(b);
    double dy = (b.yyPos - yyPos);
    return (F * dy / r);
  }
  public double calcNetForceExertedByX(Body[] b) {
    double nFx = 0;
    for (int i = 0; i < b.length; i = i + 1) {
      if (!(this.equals(b[i]))) {
          nFx = nFx + this.calcForceExertedByX(b[i]);
      }
    }
    return nFx;
  }
  public double calcNetForceExertedByY(Body[] b) {
    double nFy = 0;
    for (int i = 0; i < b.length; i = i + 1) {
      if (!(this.equals(b[i]))) {
          nFy = nFy + this.calcForceExertedByY(b[i]);
      }
    }
    return nFy;
  }
  public void update(double dt, double fX, double fY) {
    double anx = (fX / mass);
    double any = (fY / mass);
    xxVel = (xxVel + anx * dt);
    yyVel = (yyVel + any * dt);
    xxPos = (xxPos + xxVel * dt);
    yyPos = (yyPos + yyVel * dt);
  }
  public void draw() {
    StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
  }
}
