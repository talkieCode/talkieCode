import { Text } from "../../../shared";

export function LoginTitle() {
  return (
    <div className="flex flex-col items-center gap-[6px]">
      <Text type="title" size="2" weight="bold" color="white">
        돌아오신 것을 환영해요!
      </Text>
      <Text type="body" size="4" weight="normal" color="white">
        다시 만나다니 너무 반가워요!
      </Text>
    </div>
  );
}
