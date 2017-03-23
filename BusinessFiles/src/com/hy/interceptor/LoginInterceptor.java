package com.hy.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * ClassName: LoginInterceptor 未使用
 * @Description: TODO
 * @author wwh
 */
public class LoginInterceptor implements Interceptor {

	
	private static final long serialVersionUID = 1L;
	private String ignoreActions; // 通过配置文件接受的参数,忽略的action
	public String getIgnoreActions() {
		return ignoreActions;
	}

	public void setIgnoreActions(String ignoreActions) {
		this.ignoreActions = ignoreActions;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		//System.out.println(ActionContext.getContext().getSession().get("loginteacher"));;
		ActionContext actionContext = invocation.getInvocationContext();
		String currentAction = invocation.getProxy().getActionName();// 获得当前action的name
		if (ignoreActions != null) {// 如果存在忽略的action
			String actions[] = ignoreActions.split(",");// 获得忽略拦截的action
			for (String actionName : actions) {
				//System.out.println("当前:"+currentAction);
				if (currentAction.contains(actionName)) {
					return invocation.invoke();// 不进行拦截
				}
			}
		}
		Map<String, Object> session = actionContext.getSession();
		Object currentUser = session.get("loginteacher");
		String result = null;
		//System.out.println(currentUser);
		if (currentUser != null) {
			result = invocation.invoke();
			return result;
		} else {
			//result = "logout";//没有登录,执行退出,重新登录
			result = "userlogout";//没有登录,执行退出,重新登录
		}
		
		return result;
		
		
		/*ActionContext actionContext = invocation.getInvocationContext();
		String currentAction = invocation.getProxy().getActionName();// 获得当前action的name
		Map<String, Object> session = actionContext.getSession();// 获得保存的session对象
		HttpServletRequest serverRequest = ServletActionContext.getRequest();
		return invocation.invoke();// 不进行拦截*/
	}

}
