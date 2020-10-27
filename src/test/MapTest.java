package test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {

		Map<String, String> map = new HashMap<String, String>();

		System.out.println("要素数:" + map.size());

		map.put("email", "入力してね");
		map.put("name", "長すぎるよ");
		map.put("password", "もっと複雑にしてね");

		for (String str : map.keySet()) {
			System.out.println(str);
		}

		for (String str : map.values()) {
			System.out.println(str);
		}

		System.out.println("email:" + map.get("email"));
		System.out.println("name:" + map.get("name"));
		System.out.println("password:" + map.get("password"));

		System.out.println("存在しないキーは?:" + map.get("hoge")); // 存在しないキーはnullが返ってくる

		System.out.println("要素数:" + map.size());

		// 重複するキーに値を入れてみる
		map.put("email", "形式が正しくないで");
		System.out.println("email:" + map.get("email"));

	}

}
