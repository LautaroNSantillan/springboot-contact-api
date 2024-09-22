import React from "react";

//destructure prop
const Header = ({ toggleModal, nOfContacts }) => {
  return (
    <header className="header">
      <div className="container">
        <h3>Contact List ({nOfContacts}) </h3>
        <button className="btn" onClick={() => toggleModal(true)}>
          <i className="bi bi-plus-square"></i>
          Add Contact
        </button>
      </div>
    </header>
  );
};

export default Header;
