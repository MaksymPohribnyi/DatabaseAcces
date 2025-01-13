package ua.com.pohribnyi.jdbcpractise.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.com.pohribnyi.jdbcpractise.util.enums.PostStatus;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "content")
	private String content;

	@Column(name = "created_at", updatable = false)
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	@ManyToMany
	@JoinTable(name = "label_post", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
	@ToString.Exclude
	private List<Label> labels;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private PostStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writter_id", referencedColumnName = "id")
	@ToString.Exclude
	private Writter writter;

}
