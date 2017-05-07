package com.ady.connect.GameHelpers;

import com.ady.connect.Connect;
import com.ady.connect.GameObjects.Container;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ContainerOrganizer {

	private int width;
	private int height;
	private boolean symmetryy;
	public int numberOfContainer;
	public Array<Array<Vector2>> parts = new Array<Array<Vector2>>();
	public Array<Integer> numbers = new Array<Integer>();
	public int numbertotals=0;
	private int stage = 0;
	private boolean located=false;

	public ContainerOrganizer(int width, int height, boolean symmetryy) {
		this.width = width;
		this.height = height;

		this.symmetryy = symmetryy;
	}

	public ContainerOrganizer(int width, int height) {
		this(width, height, true);
	}

	public ContainerOrganizer() {
		this(800, 400, true);
	}

	public void locate(int numberOfContainer, int stage,int seed) {
		located=true;
		this.stage = stage;
		MathUtils.random.setSeed(seed);
		if (numberOfContainer <= 1)
			return;
		this.numberOfContainer = numberOfContainer;
		parts.clear();
		set(numberOfContainer);
		reArrange();
		rePositionAll();
		placeNumbers();
		// System.out.println(this.numberOfContainer);
	}
    public boolean isLocated()
    {
    	return located;
    }
	public Array<Container> assign() {
		Array<Container> container = new Array<Container>();
		int k = 0;
		for (int i = 0; i < parts.size; i++) {
			for (int j = 0; j < parts.get(i).size; j++) {
				container.add(new Container(parts.get(i).get(j), numbers.get(k++).intValue()));
			}
		}
		return container;
	}

	private void set(int numberOfContainer) {

		int nOC = numberOfContainer;
		int columns = Math.max(2, MathUtils.random(nOC / 2, nOC / 2 + 1));
		int col = 0;
		int gap = width / columns;
		// System.out.println(columns + "-" + numberOfContainer);
		while (col < columns && nOC > 0) {
			Array<Vector2> part = new Array<Vector2>();
			int rows = MathUtils.random(Math.max(1, (int) Math.floor((float) numberOfContainer / 5f)),
					(int) Math.min(4, Math.ceil((float) numberOfContainer / 2f)));
			if (col == columns - 1 || rows > nOC) {
				rows = nOC;
			}

			nOC -= rows;
			int gapy = 0;
			if (rows != 0) {
				gapy = height / rows;
			}
			int row = 0;
			while (row < rows) {

				int x, y;

				x = (int) ((Connect.width - width) / 2 + gap * (col + 0.5f));
				y = (int) ((Connect.height - height) / 2 + gapy * (row + 0.5f));

				part.add(new Vector2(x, y));
				row++;
			}
			parts.add(part);
			col++;

		}

	}

	private void placeNumbers() {
		numbers.clear();
		int total = MathUtils.random(stage * 2 + 3, stage * 3 + 6);
		numbertotals=total;
		int totaltemp = total;

		for (int i = 0; i < numberOfContainer / 2 - 1; i++) {
			int numb = MathUtils.random(2 * total / numberOfContainer) + total / numberOfContainer;
			if (numb < 1)
				numb++;
			numbers.add(numb);
			totaltemp -= numb;
		}
		int average = total / (numberOfContainer / 2);
		while (totaltemp <= average / 2) {
			int choosen = MathUtils.random(numberOfContainer / 2 - 2);
			if (numbers.get(choosen) > average / 2) {
				int a = numbers.get(choosen).intValue();
				numbers.set(choosen, a - 1);
				totaltemp++;
			}
		}

		numbers.add(totaltemp);

		System.out.println("1->" + numbers.toString());
		totaltemp = total;
		for (int i = numberOfContainer / 2; i < numberOfContainer - 1; i++) {
			int numb = MathUtils.random(total / (numberOfContainer - numberOfContainer / 2))
					+ total / numberOfContainer;
			if (numb < 1)
				numb++;
			numbers.add(numb);
			totaltemp -= numb;
		}

		average = total / (numberOfContainer - numberOfContainer / 2);
		System.out.println(average);
		while (totaltemp <= average / 2) {
			System.out.println("noc:" + numberOfContainer + "  size:" + numbers.size);
			int choosen = MathUtils.random(numberOfContainer / 2, numberOfContainer - 2);
			if (numbers.get(choosen) > average / 2) {
				int a = numbers.get(choosen).intValue();
				numbers.set(choosen, a - 1);
				totaltemp++;
			}
		}

		numbers.add(totaltemp);
		numbers.shuffle();
		System.out.println("2->" + numbers.toString());
	}

	private void reArrange() {
		int realpartssize = 0;
		for (int i = 0; i < parts.size; i++) {
			if (parts.get(i).size != 0) {
				realpartssize++;
			}

		}
		int temprealpartssize = 0;// realpartssize;

		int gap = width / (realpartssize - 1);

		float changeofgap = 0f;
		if (gap - (Connect.width - width) / 2f > 50) {
			changeofgap = MathUtils.random(50, (float) gap - (Connect.width - width) / 2f);
			// System.out.println("--------->" + changeofgap);
		}
		for (int i = 0; i < parts.size; i++) {
			if (parts.get(i).size != 0) {
				for (int j = 0; j < parts.get(i).size; j++) {
					parts.get(i).get(j).x = (int) ((Connect.width - width + changeofgap) / 2f
							+ (gap - changeofgap) * (float) temprealpartssize);
				}
				temprealpartssize++;
			}
			int xMovement = (int) (gap * MathUtils.randomSign() * MathUtils.random(0.1f, 0.4f));
			if (parts.get(i).size % 2 == 1 && parts.get(i).size > 1) {
				if (temprealpartssize == 1 && xMovement < 0 || temprealpartssize == realpartssize && xMovement > 0) {
					xMovement *= -1;
				}

				if (MathUtils.randomBoolean()) {
					// while()
					for (int j = 1; j < parts.get(i).size; j += 2) {
						parts.get(i).get(j).x += xMovement;
					}
				} else {
					for (int j = 0; j < parts.get(i).size; j += 2) {
						parts.get(i).get(j).x += xMovement;
					}
				}

			} else if (parts.get(i).size >= 4) {

				int maxi = parts.get(i).size - 1;
				int xChange = MathUtils.random(0, maxi);

				int changeone = xMovement;
				int changetwo = xMovement;

				if (temprealpartssize == 1) {
					if (changeone < 0) {
						parts.get(i).get(xChange).x -= changeone;
					} else {
						parts.get(i).get(xChange).x += changeone;
					}
					if (changetwo < 0) {
						parts.get(i).get(maxi - xChange).x -= changetwo;
					} else {
						parts.get(i).get(maxi - xChange).x += changetwo;
					}
				} else if (temprealpartssize == realpartssize) {

					if (changeone > 0) {
						parts.get(i).get(xChange).x -= changeone;
					} else {
						parts.get(i).get(xChange).x += changeone;
					}
					if (changetwo > 0) {
						parts.get(i).get(maxi - xChange).x -= changetwo;
					} else {
						parts.get(i).get(maxi - xChange).x += changetwo;
					}

				} else {
					parts.get(i).get(xChange).x += changeone;
					parts.get(i).get(maxi - xChange).x += changetwo;
				}

			}
		}

		for (int i = 0; i < parts.size; i++) {
			for (int j = 0; j < parts.get(i).size; j++) {

				for (int k = 0; k < parts.size; k++) {
					for (int l = 0; l < parts.get(k).size; l++) {
						if (k != i || l != j) {
							if (parts.get(i).get(j).dst2(parts.get(k).get(l)) < 9000f) {
								System.out.println("WTFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
								reArrange();
							}
						}
					}
				}
			}

		}
	}

	private void rePositionAll() {
		int xx = 0;
		int yy = 0;
		for (int i = 0; i < parts.size; i++) {
			if (parts.get(i).size != 0) {
				for (int j = 0; j < parts.get(i).size; j++) {

					xx += parts.get(i).get(j).x;
					yy += parts.get(i).get(j).y;
				}

			}
		}
		xx /= numberOfContainer;
		yy /= numberOfContainer;
		int diffx = xx - 480;
		int diffy = yy - 270;
		System.out.println(diffx + "***" + diffy);
		for (int i = 0; i < parts.size; i++) {
			if (parts.get(i).size != 0) {
				for (int j = 0; j < parts.get(i).size; j++) {

					parts.get(i).get(j).x -= diffx;
					parts.get(i).get(j).y -= diffy;
				}

			}
		}

	}

}
