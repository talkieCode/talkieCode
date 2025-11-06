import { LoginButtonGroup } from "./LoginButtonGroup";
import { LoginTitle } from "./LoginTitle";

export function LoginForm() {
  return (
    <div className="flex flex-col gap-[40px] p-[30px] bg-[var(--black-500)] rounded-[var(--radius-md)] w-full">
      <LoginTitle />
      <LoginButtonGroup />
    </div>
  );
}
