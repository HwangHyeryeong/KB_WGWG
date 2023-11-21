package kb.wgwg;

import kb.wgwg.article.domain.Article;
import kb.wgwg.user.domain.User;
import kb.wgwg.article.repository.ArticleRepository;
import kb.wgwg.banking.repository.BankingRepository;
import kb.wgwg.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Commit
@Transactional
class WgwgApplicationTests {
	@Autowired
	private UserRepository userRep;

	@Autowired
	private ArticleRepository articleRep;

	@Autowired
	private BankingRepository bankingRep;

	@Test
	void contextLoads() {

	}

	@Test
	void userInsert() {
		userRep.save(new User("장희정", "a@naver.com", "jang", "1234"));
		userRep.save(new User("이가현", "b@naver.com", "hyun", "1234"));
		userRep.save(new User("이찬범", "c@naver.com", "chan", "1234"));
		userRep.save(new User("나경률", "d@naver.com", "na", "1234"));
	}

	@Test
	void articleInsert() {
		User theUser = userRep.findById(2L).orElseThrow();
		articleRep.save(new Article("제목1", "내용1", "카테고리1", theUser));
		articleRep.save(new Article("제목2", "내용1", "카테고리1", theUser));
		articleRep.save(new Article("제목3", "내용1", "카테고리1", theUser));

	}

	@Test
	void bankingInsert() {
//		User theUser = userRep.findById(1L).orElseThrow();
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
//		bankingRep.save(new Banking(500, "출금", "밥", theUser, "내용1"));
	}

	@Test
	void nChallengeInsert() {

	}
}
