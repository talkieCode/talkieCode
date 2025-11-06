import {
  KAKAO,
  SocialLoginButtons,
} from "../../../features/login/social-login";
import {
  GOOGLE,
  NAVER,
} from "../../../features/login/social-login/config/constants";

export function LoginButtonGroup() {
  return (
    <div className="w-full flex flex-col gap-[10px]">
      <SocialLoginButtons
        src={KAKAO.src}
        content={KAKAO.content}
        backgroundColor={KAKAO.backgroundColor}
        textColor={KAKAO.textColor}
      />

      <SocialLoginButtons
        src={NAVER.src}
        content={NAVER.content}
        backgroundColor={NAVER.backgroundColor}
        textColor={NAVER.textColor}
      />

      <SocialLoginButtons
        src={GOOGLE.src}
        content={GOOGLE.content}
        backgroundColor={GOOGLE.backgroundColor}
        textColor={GOOGLE.textColor}
      />
    </div>
  );
}
