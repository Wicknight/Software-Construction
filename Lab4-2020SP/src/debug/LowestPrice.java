package debug;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * In a Store, there are some kinds of items to sell. Each item has a price.
 * 
 * However, there are some special offers, and a special offer consists of one
 * or more different kinds of items with a sale price.
 * 
 * You are given the each item's price, a set of special offers, and the number
 * we need to buy for each item. The job is to output the lowest price you have
 * to pay for exactly certain items as given, where you could make optimal use
 * of the special offers.
 * 
 * Each special offer is represented in the form of an array, the last number
 * represents the price you need to pay for this special offer, other numbers
 * represents how many specific items you could get if you buy this offer.
 * 
 * You could use any of special offers as many times as you want.
 * 
 * Example 1:
 * 
 * Input: [2,5], [[3,0,5],[1,2,10]], [3,2] Output: 14
 * 
 * Explanation:
 * 
 * There are two kinds of items, A and B. Their prices are $2 and $5
 * respectively.
 * 
 * In special offer 1, you can pay $5 for 3A and 0B
 * 
 * In special offer 2, you can pay $10 for 1A and 2B.
 * 
 * You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer
 * #2), and $4 for 2A.
 * 
 * Example 2:
 * 
 * Input: [2,3,4], [[1,1,0,4],[2,2,1,9]], [1,2,1] Output: 11
 * 
 * Explanation:
 * 
 * The price of A is $2, and $3 for B, $4 for C.
 * 
 * You may pay $4 for 1A and 1B, and $9 for 2A ,2B and 1C.
 * 
 * You need to buy 1A ,2B and 1C, so you may pay $4 for 1A and 1B (special offer
 * #1), and $3 for 1B, $4 for 1C.
 * 
 * You cannot add more items, though only $9 for 2A ,2B and 1C.
 * 
 * 
 * Note:
 * 
 * 1. There are at most 6 kinds of items, 100 special offers.
 * 
 * 2. For each item, you need to buy at most 6 of them.
 * 
 * 3. You are not allowed to buy more items than you want, even if that would
 * lower the overall price.
 * 
 * ---------------------------------------------------------------------------------------
 * Please debug and fix potential bugs in the following code and make it execute correctly,
 * robust, and completely in accordance with above requirements.
 * 
 * DON'T change the initial logic of the code.
 * ---------------------------------------------------------------------------------------
 * 
 */
public class LowestPrice {

	/**
	 * ����һ�鵥�ۡ���������֪����Ʒ�ڸ������Ż������»��ѵ���ͼ۸񣬵�����������Ӧ��ƥ�䣬�Ҳ�ӦΪ�ա���price�Ĵ�С��needs�Ĵ�С���
	 * @param price ������Ʒ�ĵ���(price.size()Ӧ����needs.size()����Ҳ�Ϊ��)
	 * @param special �������в�����Ʒ���Ż�
	 * @param needs ������Ʒ��������
	 * @return ������Ʒ���ѵ���ͼ۸�
	 */
	public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
		return shopping(price, special, needs);
	}

	/**
	 * ����һ�鵥�ۡ���������֪����Ʒ�ڸ������Ż������»��ѵ���ͼ۸񣬵�����������Ӧ��ƥ�䣬�Ҳ�ӦΪ�ա���price�Ĵ�С��needs�Ĵ�С���
	 * @param price ������Ʒ�ĵ���(price.size()Ӧ����needs.size()����Ҳ�Ϊ��)
	 * @param special �������в�����Ʒ���Ż�
	 * @param needs ������Ʒ��������
	 * @return ������Ʒ���ѵ���ͼ۸�
	 */
	public int shopping(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
		int j = 0, res = dot(needs, price);
		if(special==null||special.isEmpty())//û���Żݣ������ж�
			return res;
		for (List<Integer> s : special) {//���offer������̽��ÿ�ν�һ��special offer���롰���ﳵ�����������󣬵ݹ����
										 //ѡ��һ��offer����ȫ����̽�Ժ�res��Ϊ�����̽������Сֵ��Ȼ����һ��offer��Ϊ����ѡȡ�������ظ���̽
										 //�ٴν�����֤��resά����С���Ծͱ�֤������Щ��̽����е���С�����
			List<Integer> clone = new ArrayList<>(needs);

			for (j = 0; j < needs.size(); j++) {
				int diff = clone.get(j) - s.get(j);
				if (diff < 0)//<=0��Ϊ<0����Ϊdiff=0˵����offerǡ�����itemȫ�����򣬷���Ҫ��
					break;
				clone.set(j, diff);
			}
			if (j == needs.size()) {//s��offer�ܼ۸���s.get(needs.size())��
				res = Math.min(res, s.get(j) + shopping(price, special, clone));
			}
		}
		return res;
	}

	/**
	 * ͨ��һ����Ʒ�ĵ���������������ܼۣ�������������Ӧ��ƥ�䣬��a�Ĵ�С��b�Ĵ�С��ȣ��Ҳ�ӦΪ��
	 * @param a ÿ����Ʒ��Ҫ������(a.size()Ӧ����b.size()����Ҳ�Ϊ��)
	 * @param b	ÿ����Ʒ��Ҫ�ĵ���()
	 * @return ��һ����Ʒ���ܼ�
	 */
	public int dot(List<Integer> a, List<Integer> b) {//���㲻��offerʱ���ܼ�
		int sum = 0;
		for (int i = 0; i < a.size(); i++) {
			sum += a.get(i) * b.get(i);
		}
		return sum;
	}

}