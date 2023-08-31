package main.wonprice.domain.product.entity;

import lombok.*;
import main.wonprice.domain.member.entity.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private Long buyerId; // 구매자

    private String title; // 글 제목

    @Lob
    private String description; // 내용

    private Long immediatelyBuyPrice; // 즉시 구매가

    private Long currentAuctionPrice; // 현재 경매가

    private Boolean auction; // 경매 여부

    private LocalDateTime createAt; // 거래 시작 시간 (작성시간)

    private LocalDateTime deletedAt; // 글 삭제 시간

    private LocalDateTime modifiedAt; // 글 수정 시간

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // 상품 거래 상태

    private Long views; // 조회수

    private LocalDateTime closedAt; // 경매 종료 시간

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member seller; // 판매자
    
    private Boolean removed; // 글 삭제 여부 (게시글 삭제 시, 실제 DB에서 삭제되는 것 X true, false 로 명시)

    @Builder
    public Product(Member seller, String title, String description, Long immediatelyBuyPrice) {
        this.seller = seller;
        this.title = title;
        this.description = description;
        this.immediatelyBuyPrice = immediatelyBuyPrice;
    }

    // 상품 정보 등록 날짜 갱신 메서드
    @PrePersist
    public void prePersist() {
        createAt = LocalDateTime.now();
    }

    // 상품 정보 수정 날짜 갱신 메서드
    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
