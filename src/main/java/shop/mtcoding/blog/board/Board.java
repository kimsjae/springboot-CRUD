package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.core.annotation.Order;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_tb")
@Data
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    @Transient // 컬럼 추가 안 됨
    private boolean isBoardOwner;

    /**
     * fk제약조건 때문에 게시글을 삭제하면 댓글이 참조하는 게시글이 사라지기 때문에 삭제가 될 수 없음.
     * <p>1. 댓글을 먼저 삭제하고, 게시글 삭제.</p>
     * <p>2. 댓글의 fk에 값을 null로 변경하고 게시글 삭제.</p>
     * <p>3. cascade 어노테이션을 이용하기.</p>
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    private List<Reply> replies = new ArrayList<>();

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(BoardRequest.UpdateDTO reqDTO) {
        this.title = reqDTO.getTitle();
        this.content = reqDTO.getContent();
    }
}
