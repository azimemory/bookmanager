package com.kh.bookmanager.common.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.kh.bookmanager.common.exception.CustomException;

import oracle.jdbc.pool.OracleDataSource;

@Aspect
@Configuration
public class TransactionConfig {
	
	private static final int TX_METHOD_TIMEOUT = 3;
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.kh.bookmanager..*.*ServiceImpl.*(..))";
    private DataSourceTransactionManager transactionManager;
   
    @Autowired
    private EntityManagerFactory emf;
    
    @Bean
    public JpaTransactionManager jpaTransactionManager() {
    	JpaTransactionManager jtm = new JpaTransactionManager();
    	jtm.setEntityManagerFactory(emf);
    	return jtm;
    }
    
    @Bean
    public TransactionInterceptor txAdvice() {
    	TransactionInterceptor txAdvice = new TransactionInterceptor();
    	Properties txAttributes = new Properties();
    	List<RollbackRuleAttribute> rollbackRules = new ArrayList<RollbackRuleAttribute>();
    	rollbackRules.add(new RollbackRuleAttribute(Exception.class));
    	rollbackRules.add(new RollbackRuleAttribute(CustomException.class));

    	/** If need to add additionall exceptio, add here **/
    	DefaultTransactionAttribute readAttr = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
    	readAttr.setReadOnly(true);
    	readAttr.setTimeout(TX_METHOD_TIMEOUT);

        RuleBasedTransactionAttribute writeAttr = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
        writeAttr.setTimeout(TX_METHOD_TIMEOUT);

		// read-only
		txAttributes.setProperty("select*", readAttr.toString());

		// write rollback-rule
		txAttributes.setProperty("insert*", writeAttr.toString());
		txAttributes.setProperty("update*", writeAttr.toString());
		txAttributes.setProperty("delete*", writeAttr.toString());
		txAdvice.setTransactionAttributes(txAttributes);
		
		txAdvice.setTransactionManager(transactionManager);
        return txAdvice;
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.kh.bookmanager..*.*ServiceImpl.*(..))");
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}

