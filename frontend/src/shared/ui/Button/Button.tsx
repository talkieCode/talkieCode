import classNames from "classnames";
import type { PropsWithChildren } from "react";
import styles from "./Button.module.css";

type Props = PropsWithChildren<{
  type?: "primary" | "default";
  style?: "fill" | "outline";
  size?: "big" | "medium";
  rd?: "xs" | "sm" | "md" | "lg" | "xl" | "2xl";
  className?: string;
  onClick?: React.MouseEventHandler<HTMLElement>;
  disabled?: boolean;
}>;

export function Button(props: Props) {
  const {
    type = "primary",
    style = "outline",
    size = "medium",
    disabled,
    children,
    className,
    ...rest
  } = props;

  return (
    <button
      type="button"
      className={classNames(
        styles.button,
        styles[`button--type-${type}`],
        styles[`button--style-${style}`],
        styles[`button--size-${size}`],
        styles[`button--rd`],
        "font-semibold",
        className
      )}
      disabled={disabled}
      {...rest}
    >
      <span className="button__content">{children}</span>
    </button>
  );
}