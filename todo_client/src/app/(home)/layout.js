import PropTypes from "prop-types";
import Header from "@/components/Header";

export default async function Layout({ children }) {
  return (
    <>
      <Header />
      {children}
    </>
  );
}
Layout.propTypes = {
  children: PropTypes.node,
};