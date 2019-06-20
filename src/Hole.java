
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Hole {
	private int id,x,y;
	private int pixelDiameter;
	private int offset;
	private float diameter;
	private float depth;
	private int traffic;
	private Color c;
	public JButton hoverable;
	public JLabel tt_id;
	public JLabel tt_diam;
	public JLabel tt_depth;
	public JLabel tt_volume;
	public JLabel tt_cost;
	public int region = -1;
	
	public Hole(int id,int x,int y,float diameter, float depth, int traffic) {
		hoverable = new JButton();
		hoverable.setOpaque(false);
		hoverable.setContentAreaFilled(false);
		hoverable.setBorderPainted(false);
		hoverable.setBounds(x, y, 20, 20);
		setId(id);
		setX(x);
		setY(y);
		setDiameter(diameter);
		setDepth(depth);
		setTraffic(traffic);
		setPixelDiameter(20);
		setOffset(getPixelDiameter()/4);
		tt_id = new JLabel("ID:"+id);
		tt_id.setBackground(Color.white);
		tt_id.setOpaque(true);
		tt_id.setVerticalAlignment(1);
		tt_id.hide();
		tt_diam = new JLabel(new String(String.format("Diameter:%.3f m",getDiameter())));
		tt_diam.setBackground(Color.white);
		tt_diam.setOpaque(true);
		tt_diam.setVerticalAlignment(1);
		tt_diam.hide();
		tt_depth = new JLabel(new String(String.format("Depth:%.3f m",getDepth())));
		tt_depth.setBackground(Color.white);
		tt_depth.setOpaque(true);
		tt_depth.setVerticalAlignment(1);
		tt_depth.hide();
		tt_volume = new JLabel(new String(String.format("Volume:%.3f m^3",getDepth()*getDiameter())));
		tt_volume.setBackground(Color.white);
		tt_volume.setOpaque(true);
		tt_volume.setVerticalAlignment(1);
		tt_volume.hide();
		tt_cost = new JLabel(new String(String.format("Cost:$%.2f",getCost())));
		tt_cost.setBackground(Color.white);
		tt_cost.setOpaque(true);
		tt_cost.setVerticalAlignment(1);
		tt_cost.hide();
		if(y+38>=1900) {
			tt_id.setBounds(getX()+getPixelDiameter(), 1900-42, 130, 14);
			tt_diam.setBounds(getX()+getPixelDiameter(), 1900-42+14, 130, 14);
			tt_depth.setBounds(getX()+getPixelDiameter(), 1900-42+28, 130, 14);
			tt_volume.setBounds(getX()+getPixelDiameter(), 1900-42+42, 130, 14);
			tt_cost.setBounds(getX()+getPixelDiameter(), 1900-42+56, 130, 14);
		}else{
			tt_id.setBounds(getX()+getPixelDiameter(), getY(), 130, 14);
			tt_diam.setBounds(getX()+getPixelDiameter(), getY()+14, 130, 14);
			tt_depth.setBounds(getX()+getPixelDiameter(), getY()+28, 130, 14);
			tt_volume.setBounds(getX()+getPixelDiameter(), getY()+42, 130, 14);
			tt_cost.setBounds(getX()+getPixelDiameter(), getY()+56, 130, 14);
		}
		hoverable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tt_id.show();
				tt_diam.show();
				tt_depth.show();
				tt_volume.show();
				tt_cost.show();
			}
		});
		hoverable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				tt_id.hide();
				tt_diam.hide();
				tt_depth.hide();
				tt_volume.hide();
				tt_cost.hide();
			}
		});
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getDiameter() {
		return diameter;
	}

	public void setDiameter(float diameter) {
		this.diameter = diameter;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public int getTraffic() {
		return traffic;
	}

	public void setTraffic(int traffic) {
		this.traffic = traffic;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPixelDiameter() {
		return pixelDiameter;
	}

	public void setPixelDiameter(int pixelDiameter) {
		this.pixelDiameter = pixelDiameter;
		this.setOffset(pixelDiameter/4);
		this.hoverable.setBounds(this.getX()-this.getOffset(),this.getY()-this.getOffset(), this.getPixelDiameter(), this.getPixelDiameter());
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public double getCost() {
		return (50*((this.getDepth()*this.getDiameter())/0.179));
	}

}
