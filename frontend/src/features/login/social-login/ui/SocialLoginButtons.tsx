import { Button, Text } from "../../../../shared";

type SocialLoginButtonsProps = {
  src: string;
  content: string;
  backgroundColor: string;
  textColor: string;
};

export function SocialLoginButtons({
  src,
  content,
  backgroundColor,
  textColor,
}: SocialLoginButtonsProps) {
  return (
    <Button className={`${backgroundColor} w-full`}>
      <Text className="relative block" color={textColor} weight="medium">
        <img src={src} className="absolute left-[32px]" />
        {content}
      </Text>
    </Button>
  );
}
