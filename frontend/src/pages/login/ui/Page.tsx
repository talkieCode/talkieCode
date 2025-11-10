import { LoginLayout } from "../../../shared";
import { LoginForm } from "../../../widgets/login";

export function LoginPage() {
  return (
    <div className="w-screen h-screen bg-black relative">
      <img
        src="/d8680b1c1576ecc8.svg"
        alt="Logo"
        className="fixed top-0 left-0 w-full h-full object-cover"
      />
      <LoginLayout>
        <LoginForm />
      </LoginLayout>
    </div>
  );
}
