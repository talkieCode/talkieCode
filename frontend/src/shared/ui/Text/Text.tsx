import React from "react";
import classNames from "classnames";
import styles from "./Text.module.css";

interface TextProps {
  children: React.ReactNode;
  className?: string;
  type?: "title" | "body" | "caption";
  size?: "1" | "2" | "3" | "4";
  weight?: "normal" | "medium" | "semibold" | "bold";
  color?: string;
  align?: "left" | "center" | "right";
}

export const Text: React.FC<TextProps> = ({
  children,
  type = "body",
  size = "1",
  weight = "normal",
  color = "black",
  align = "center",
  className,
}) => {
  return (

    <span
      className={classNames(
        styles.button,
        styles[`text--type-${type}`],
        styles[`text--weight-${weight}`],
        styles[`text--size-${size}`],
        styles[`text--align-${align}`],
        className
      )}
      style={{ color }}
    >
      {children}
    </span>
  );
};