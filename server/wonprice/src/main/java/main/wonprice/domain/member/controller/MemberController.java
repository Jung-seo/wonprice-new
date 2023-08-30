package main.wonprice.domain.member.controller;

import lombok.extern.java.Log;
import main.wonprice.domain.member.dto.MemberPatchDto;
import main.wonprice.domain.member.dto.MemberPostDto;
import main.wonprice.domain.member.dto.MemberResponseDto;
import main.wonprice.domain.member.entity.Member;
import main.wonprice.domain.member.mapper.MemberMapper;
import main.wonprice.domain.member.service.MemberService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;
    private MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postMember(@RequestBody @Valid MemberPostDto postDto) {

        Member member = mapper.postDtoToMember(postDto);
        Member createdMember = memberService.joinMember(member);
        MemberResponseDto response = mapper.memberToResponseDto(createdMember);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    /* 로그인한 회원 정보(마이페이지 정보) -> 로그인 구현 후 구현 예정
    @GetMapping
    public ResponseEntity getMember()
    */

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") Long memberId) {

        Member findMember = memberService.findMember(memberId);
        MemberResponseDto response = mapper.memberToResponseDto(findMember);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getMembers(Pageable pageable) {

        List<Member> members = memberService.findMembers(pageable);
        List<MemberResponseDto> response = mapper.membersToResponseDtos(members);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") Long memberId,
                                      @RequestBody @Valid MemberPatchDto patchDto) {

        Member member = mapper.patchDtoToMember(patchDto);
        member.setMemberId(memberId);
        Member patchedMember = memberService.updateMember(member);

        MemberResponseDto response = mapper.memberToResponseDto(patchedMember);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
