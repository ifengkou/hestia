package cn.ifengkou.hestia.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

public class Kit {
	private static final String HexStr = "0123456789ABCDEF";

	public static final String toHex(byte b) {
		return ("" + HexStr.charAt(0xf & b >> 4) + HexStr.charAt(b & 0xf));
	}

	public static final String fmtHex(String h, int bc) {
		if (h == null) {
			return null;
		}
		String f = "";
		int dc = 0;
		while (dc < bc * 2 || dc % 2 == 1) {
			if (dc % 2 == 0 && dc != 0) {
				f = "-" + f;
			}
			int i = h.length() - dc;
			if (i > 0) {
				f = h.substring(i - 1, i).toUpperCase() + f;
			} else {
				f = "0" + f;
			}
			dc++;
		}
		return f;
	}

	public static final String toHex(int i, int bc) throws Exception {
		if (bc < 0 || bc > 4) {
			throw new Exception(
					"Out of range: String toHex(int i, int bc), the value of bc must between 0 and 4.");
		}
		int max = 0x7FFFFFFF;
		if (bc > 0 && bc < 4) {
			max = 1;
			for (int c = 0; c < bc; c++) {
				max = max * 256;
			}
			max = max - 1;
		}
		if (i < 0 || i > max) {
			throw new Exception("Out of range: String toHex(int i, int bc) ,when bc=" + bc
					+ " then the value of i must between 0 and " + max + ".");
		}
		String h = Integer.toHexString(i);
		if (bc > 0) {
			return fmtHex(h, bc);
		} else {
			int bc2 = (int) ((h.length() + 1) / 2);
			return fmtHex(h, bc2);
		}
	}

	public static final String toHex(int i) throws Exception {
		return toHex(i, 0);
	}

	public static final String toHex(byte[] ba, int len) {
		if (ba == null) {
			return null;
		}
		String s = "";
		for (int i = 0; i < len; i++) {
			if (i < ba.length) {
				String h = toHex(ba[i]);
				if (s.length() == 0) {
					s = h;
				} else {
					s = s + "-" + h;
				}
			} else {
				if (s.length() == 0) {
					s = "00";
				} else {
					s = "00-" + s;
				}
			}
		}
		return s;
	}

	public static final String toHex(byte[] ba) {
		if (ba == null) {
			return null;
		} else {
			return toHex(ba, ba.length);
		}
	}

	public static final byte toByte(String h) {
		String t = h.replaceAll("-", "");
		t = t.substring(t.length() - 2);
		byte b1 = (byte) HexStr.indexOf(t.substring(0, 1));
		byte b2 = (byte) HexStr.indexOf(t.substring(1, 2));
		return (byte) ((b1 << 4) | b2);
	}

	public static final byte toByte(int i) throws Exception {
		if (i < 0 || i > 255) {
			throw new Exception("Out of range: byte toByte(int i), the value of i must between 0 and 255.");
		}
		return toByte(toHex(i));
	}

	private static final int byteHexToInt(String h) {
		String t = h.replaceAll("-", "");
		t = t.substring(t.length() - 2);
		int i1 = HexStr.indexOf(t.substring(0, 1));
		int i2 = HexStr.indexOf(t.substring(1, 2));
		return i1 * 16 + i2;
	}

	/**
	 * 鍙檺浜庡崟瀛楄妭
	 * @param b
	 * @return
	 */
	public static final int toInt(byte b) {
		return byteHexToInt(toHex(b));
	}

	// 鎶婃渶澶�4瀛楄妭byte鏁扮粍杞崲鎴怚NT绫诲瀷
	public static final int toInt(byte[] ba) throws Exception {
		if (ba.length > 4) {
			throw new Exception(
					"Out of range: int toInt(byte[] ba), the length of ba must not larger than 4.");
		}
		if (ba.length == 4 && ba[0] < 0) {
			throw new Exception(
					"Out of range: int toInt(byte[] ba), the value of ba must not larger than 7F-FF-FF-FF.");
		}
		int result = 0;
		for (int i = 0; i < ba.length; i++) {
			result = result * 256 + toInt(ba[i]);
		}
		return result;
	}

	public static final long toLong(byte[] ba) throws Exception {
		if (ba.length > 8) {
			throw new Exception(
					"Out of range: long toLong(byte[] ba), the length of ba must not larger than 8.");
		}
		if (ba.length == 8 && ba[0] < 0) {
			throw new Exception(
					"Out of range: long toLong(byte[] ba), the value of ba must not larger than 7F-FF-FF-FF-FF-FF-FF-FF.");
		}
		long result = 0;
		for (int i = 0; i < ba.length; i++) {
			result = result * 256 + toInt(ba[i]);
		}
		return result;
	}

	public static final byte[] toByteAry(int i, int bc) throws Exception {
		if (bc < 1 || bc > 4) {
			throw new Exception(
					"Out of range: byte[] toByteAry(int i, int bc) , the value of bc must between 1 and 4.");
		}
		int max = 0x7FFFFFFF;
		if (bc < 4) {
			max = 1;
			for (int c = 0; c < bc; c++) {
				max = max * 256;
			}
			max = max - 1;
		}
		if (i < 0 || i > max) {
			throw new Exception("Out of range: byte[] toByteAry(int i, int bc) ,when bc=" + bc
					+ " then the value of i must between 0 and " + max + ".");
		}
		int t = i;
		byte[] ba = new byte[bc];
		for (int p = bc - 1; p >= 0; p--) {
			ba[p] = toByte((int) (t % 256));
			t = (int) (t / 256);
		}
		return ba;
	}

	public static final byte[] toByteAry(int i) throws Exception {
		if (i < 0) {
			throw new Exception(
					"Out of range: byte[] toByteAry(int i) , the value of i must not less than 0.");
		}
		int t = i;
		int bc = 1;
		while (t != 0) {
			t = (int) (t / 256);
			if (t != 0) {
				bc++;
			}
		}
		return toByteAry(i, bc);
	}

	public static final byte[] toByteAry(long l, int bc) throws Exception {
		if (bc < 1 || bc > 8) {
			throw new Exception(
					"Out of range: byte[] toByteAry(long l, int bc) , the value of bc must between 1 and 8.");
		}
		long max = 0x7FFFFFFFFFFFFFFFL;
		if (bc < 4) {
			max = 1;
			for (int c = 0; c < bc; c++) {
				max = max * 256;
			}
			max = max - 1;
		}
		if (l < 0 || l > max) {
			throw new Exception("Out of range: byte[] toByteAry(long l, int bc), when bc=" + bc
					+ " then the value of l must between 0 and " + max + ".");
		}
		long t = l;
		byte[] ba = new byte[bc];
		for (int p = bc - 1; p >= 0; p--) {
			ba[p] = toByte((int) (t % 256));
			t = (long) (t / 256);
		}
		return ba;
	}

	public static final byte[] toByteAry(long l) throws Exception {
		if (l < 0) {
			throw new Exception(
					"Out of range: byte[] toByteAry(long l) , the value of l must not less than 0.");
		}
		long t = l;
		int bc = 1;
		while (t != 0) {
			t = (long) (t / 256);
			if (t != 0) {
				bc++;
			}
		}
		return toByteAry(l, bc);
	}

	public static final byte[] toByteAry(String hexStr) {
		String s = hexStr.replaceAll("-", "").replaceAll(" ", "").toUpperCase();
		int len = (s.length() + 1) / 2;
		if (len * 2 > s.length()) {
			s = "0" + s;
		}
		byte[] ba = new byte[len];
		for (int i = 0; i < len; i++) {
			ba[i] = toByte(s.substring(i * 2, i * 2 + 2));
		}
		return ba;
	}

	// 鎶�4瀛楄妭鐨刡yte鏁扮粍杞崲鎴愮敤鐐瑰彿鍒嗗壊鐨勫湴鍧�瀛楃涓�
	public static final String toAddress(byte[] ba) {
		String adr = null;
		if (ba != null) {
			for (int i = 0; i < ba.length; i++) {
				if (i == 0) {
					adr = "" + toInt(ba[i]);
				} else {
					adr = adr + "." + toInt(ba[i]);
				}
			}
		}
		return adr;
	}

	// 鎶婄敤鐐瑰彿鍒嗗壊鐨勫湴鍧�瀛楃涓茶浆鎹㈡垚4瀛楄妭鐨刡yte鏁扮粍
	public static final byte[] AddressToByteAry(String adr) throws NumberFormatException, Exception {
		byte[] ba = null;
		String[] ary = adr.split("\\.");
		if (ary != null && ary.length > 0) {
			ba = new byte[ary.length];
			for (int i = 0; i < ary.length; i++) {
				ba[i] = Kit.toByte(Integer.valueOf(ary[i]));
			}
		}
		return ba;
	}

	// ------------------------------------- 浠ヤ笅鍔熻兘涓嶉�傚悎 JUnit
	// 娴嬭瘯锛屼笉鍦╡t.kit.test.TestKit 绉嶆祴璇�
	// ----------------------------------------------------
	/**
	 * 鑾峰彇鎸囧畾鏂囦欢(涓�鑸槸ini)鐨凱roperties瀵硅薄
	 *
	 * @param propsName
	 * @return
	 */
	public static Properties getProperties(String propsName) {
		Properties props = new Properties();
		InputStream is = null;
		try {
			File propsFile = new File(propsName);
			is = new FileInputStream(propsFile);
			props.load(is);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (is != null)
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return props;
	};

	public static byte[] getMac() {
		byte[] macAddress = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			InetAddress[] ips = InetAddress.getAllByName(addr.getCanonicalHostName());
			if (ips != null && ips.length > 0) {
				// System.out.println("ips.length = " + ips.length);
				for (int i = 0; i < ips.length && i < 1; i++) {
					// System.out.println("Hostname: " + ips[i].getHostName()
					// + " IP: " + ips[i].getHostAddress());
					NetworkInterface network = NetworkInterface.getByInetAddress(ips[i]);
					macAddress = network.getHardwareAddress();
				}
			}
			return macAddress;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return macAddress;
	}

	/**
	 * 浠庡師byte鏁扮粍涓璫opy鍑轰竴涓暟缁勭墖娈碉紙娉ㄦ剰锛氫笉鍋氳秺鐣屾娴�,涓嶆姏鍑哄紓甯革級
	 *
	 * @param original
	 * @param index
	 * @param length
	 * @return
	 */
	public static byte[] copyOfRange(byte[] original, int index, int length) {
		byte[] copy = new byte[length];
		System.arraycopy(original, index, copy, 0, length);
		return copy;
	}

	public static byte[] combineBytes(byte[] fst, byte[] scd) {
		byte[] target = new byte[fst.length + scd.length];
		System.arraycopy(fst, 0, target, 0, fst.length);
		System.arraycopy(scd, 0, target, fst.length, scd.length);
		return target;
	}

	/**
	 * 灏唍um 杞崲涓� 闀垮害涓簂ength鐨刡yte[]
	 * 闇�娉ㄦ剰num鑼冨洿鍜宭ength鐨勫彇鍊硷紝涓嶅仛瓒婄晫澶勭悊鍜屽紓甯稿鐞�
	 * @param num
	 * @param length
	 * @return
	 */
	public static byte[] intToByte(int num, int length) {
		int temp = num;
		byte[] b = new byte[length];
		for (int i = b.length - 1; i > -1; i--) {
			b[i] = new Integer(temp & 0xff).byteValue(); // 灏嗘渶楂樹綅淇濆瓨鍦ㄦ渶浣庝綅
			temp = temp >> 8; // 鍚戝彸绉�8浣�
		}
		return b;
	}


	 /**
     * int鏁存暟杞崲涓�4瀛楄妭鐨刡yte鏁扮粍
     *
     * @param i
     *            鏁存暟
     * @return byte鏁扮粍
     */
    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    /**
     * 鐢熸垚鏍规嵁闅忔満鏁存暟 鐨勬暟缁�--鐢ㄤ簬upgrade-token鐨勭敓鎴�
     * @return
     */
    public static byte[] generateRandomIntsBytes(){
    	Random random = new Random();
    	return intToByte4(random.nextInt(65535));//淇濋櫓璧疯...//Integer.MAX_VALUE
    }

    /**
     * 4浣峛yte鏁扮粍杞琲nt锛屾暟缁勯暱搴︿笉鍋氭鏌�
     * @param b
     * @return
     * @author Sloong
     * @date 2015骞�8鏈�13鏃� 涓婂崍9:45:47
     */
    public static int byteArrayToInt(byte[] b) {
	    return   b[3] & 0xFF |
	            (b[2] & 0xFF) << 8 |
	            (b[1] & 0xFF) << 16 |
	            (b[0] & 0xFF) << 24;
	}

    /**
	 * 灏哾ata瀛楄妭鍨嬫暟鎹浆鎹负0~255 (0xFF 鍗矪YTE)銆�
	 * @param data
	 * @return
	 * @author Sloong
	 * @date 2015骞�8鏈�18鏃� 涓婂崍9:22:26
	 */
	public static int getUnsignedChar(byte data) {
		return data & 0x0FF;
	}

	/**
	 * 灏哾ata瀛楄妭鍨嬫暟鎹浆鎹负0~65535 (0xFFFF 鍗砏ORD)銆�
	 * @param data
	 * @return
	 * @author Sloong
	 * @date 2015骞�8鏈�18鏃� 涓婂崍9:22:20
	 */
	public static int getUnsignedWORD(short data) {
		return data & 0x0FFFF;
	}

	/**
	 *  灏唅nt鏁版嵁杞崲涓�0~4294967295 (0xFFFFFFFF鍗矰WORD)
	 * @param data
	 * @return
	 * @author Sloong
	 * @date 2015骞�8鏈�18鏃� 涓婂崍9:22:15
	 */
	public static long getUnsignedDWORD(int data) {
		return data & 0x0FFFFFFFFl;
	}

	public static byte transTimeQuarter(int time){
		if(time<0) return (byte)0xFF;
		byte returnQuarter;
		switch (time%60){
			case 0:
				returnQuarter = 0;
				break;
			case 15:
				returnQuarter = 1;
				break;
			case 30:
				returnQuarter = 2;
				break;
			case 45:
				returnQuarter = 3;
				break;
			default:
				returnQuarter = (byte)0xFF;
				break;
		}
		return returnQuarter;
	}

	public static int transTimeQuarter(byte time,int index){
		if(index<0||index>168) return -1;
		if(time == (byte)0xFF) return -1;
		int minutes;
		switch (time){
			case 0:
				minutes = 0;
				break;
			case 1:
				minutes = 15;
				break;
			case 2:
				minutes = 30;
				break;
			case 3:
				minutes = 45;
				break;
			default:
				minutes = -1;
				break;
		}
		int clock = index%24;
		return minutes>-1?clock*60+minutes:-1;
	}

	public static double calcTemperature(int orgin) {
		return orgin == 255?-1.0D:(double)orgin / 2.0D - 20.0D;
	}

	public static byte calXTemperature(double temp) throws Exception {
		if(temp<0) return (byte)0xFF;
		int xTemp = (int)((temp + 20.0D) * 2.0D);
		return Kit.toByte(xTemp);
	}

	public static HashMap<String,String> parsePostData(String postData){
		HashMap<String,String> map = new HashMap<>();
		String[] params = postData.split("&");
		for (String str : params){
			String[] kv = str.split("=");
			if(kv.length == 2){
				map.put(kv[0],kv[1]);
			}else{
				map.put(kv[0],"");
			}
		}
		return map;
	}
}
