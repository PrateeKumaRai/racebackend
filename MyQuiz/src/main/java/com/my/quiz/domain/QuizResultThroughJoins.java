package com.my.quiz.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.NamedNativeQuery;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;


@SqlResultSetMapping(
	    name="findByQuizIdMapping",
	    classes={
	        @ConstructorResult(
	            targetClass=QuizResultThroughJoins.class,
	            columns={
	                @ColumnResult(name="player_id", type = Long.class),
	                @ColumnResult(name="nick_name", type = String.class),
	                @ColumnResult(name="state", type = String.class),
	                @ColumnResult(name="quiz_id", type = Long.class),
	                @ColumnResult(name="question_id", type = Long.class),
	                @ColumnResult(name="score", type = Long.class),
	                @ColumnResult(name="answer_time",type= int.class)
	            }
	        )
	    }
	)
@NamedNativeQuery(name="findByQuizId",resultClass = QuizResultThroughJoins.class,resultSetMapping="findByQuizIdMapping",query="select player.player_id,player.nick_name,playquiz.state,playquiz.quiz_id,playquiz.question_id,playquiz.score,playquiz.answer_time\r\n" + 
		"from player\r\n" + 
		"INNER JOIN playquiz\r\n" + 
		"ON  player.quiz_id = playquiz.quiz_id and player.player_id=playquiz.player_id\r\n" + 
		"and playquiz.state='right' and playquiz.quiz_id = :quizId")
@Entity
@Getter
@Setter
public class QuizResultThroughJoins {

	@Id
	private Long player_id;

	@NotEmpty(message = "nick_name should not be null or empty")
	private String nick_name;

	@NotEmpty(message = "state should not be null or empty")
	private String state;

	@NotNull(message = "quizId should not be null or empty")
	private Long quiz_id;

	@NotNull(message = "questionId should not be null or empty")
	private Long question_id;

	@NotNull(message = "score should not be null or empty")
	private Long score;

	@NotNull(message = "answerTime should not be null or empty")
	private int answer_time;

	public QuizResultThroughJoins(Long player_id, @NotEmpty(message = "nick_name should not be null or empty") String nick_name,
			@NotEmpty(message = "state should not be null or empty") String state,
			@NotNull(message = "quizId should not be null or empty") Long quiz_id,
			@NotNull(message = "questionId should not be null or empty") Long question_id,
			@NotNull(message = "score should not be null or empty") Long score,
			@NotNull(message = "answerTime should not be null or empty") int answer_time) {
		super();
		this.player_id = player_id;
		this.nick_name = nick_name;
		this.state = state;
		this.quiz_id = quiz_id;
		this.question_id = question_id;
		this.score = score;
		this.answer_time = answer_time;
	}
	
	
}
