package validation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.katachi.miraino.settings.MessageSettings;
import com.katachi.miraino.util.validation.Validation;
import com.katachi.miraino.util.validation.ValidationUtil;

/**
 * TODOリスト登録・更新バリデーションクラス
 */
public class TodoValidation extends Validation {

	/**
	 * コンストラクタ
	 * @param request リクエストオブジェクト
	 */
	public TodoValidation(HttpServletRequest request) {
		super(request);
	}

	/**
	 * バリデーションを行います
	 * @return バリデーションエラーのMap<項目名, エラーメッセージ>
	 */
	public Map<String, String> validate(){
		// TODO項目のバリデーション
		if (!ValidationUtil.isMaxLength(this.request.getParameter("todoItem"), 50)) {
			this.errors.put("todoItem", String.format(MessageSettings.MSG_LENGTH_LONG, "TODO項目", 50));
		}
		if (!ValidationUtil.isMinLength(this.request.getParameter("todoItem"), 1)) {
			this.errors.put("todoItem", String.format(MessageSettings.MSG_REQUIRED, "TODO項目"));
		}
		// 登録日のバリデーション
		if (!ValidationUtil.isDate(this.request.getParameter("registrationDate"))) {
			this.errors.put("registrationDate", String.format(MessageSettings.MSG_INVALID_VALUE, "登録日"));
		}
		if (!ValidationUtil.isMinLength(this.request.getParameter("registrationDate"), 1)) {
			this.errors.put("registrationDate", String.format(MessageSettings.MSG_REQUIRED, "登録日"));
		}
		// 期限日のバリデーション
		if (!ValidationUtil.isDate(this.request.getParameter("expirationDate"))) {
			this.errors.put("expirationDate", String.format(MessageSettings.MSG_INVALID_VALUE, "期限日"));
		}
		if (!ValidationUtil.isMinLength(this.request.getParameter("expirationDate"), 1)) {
			this.errors.put("expirationDate", String.format(MessageSettings.MSG_REQUIRED, "期限日"));
		}

		return errors;
	}
}
