import type { ReactNode } from "react";

type LoginLayoutProps = {
  children: ReactNode;
};

export function LoginLayout({ children }: LoginLayoutProps) {
  return (
    <div className="relative mx-auto max-w-[520px] min-w-[350px] px-[10px] h-full flex items-center">
      {children}
    </div>
  );
}
