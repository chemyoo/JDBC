package test.function;
/** 
 * @author 作者 : jianqing.liu
 * @version 创建时间：2018年3月14日 下午1:55:19 
 * @since 2018年3月14日 下午1:55:19 
 * @description 类说明 
 */
public class DailyTest {
	
	private DailyTest() {}
	
	private static void booleanArrayTest() {
		boolean [][] boolArrat = new boolean[4][4];
		boolean flag = false;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				flag = (Math.random()*10)>5;
				boolArrat[i][j] = flag;
				if(flag) {
					System.out.print("*");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
		System.out.println("--------------------");
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(boolArrat[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		booleanArrayTest();
	}
}
