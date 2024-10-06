import React from 'react'
import { Link } from 'react-router-dom'

const Contact = ({ contact }) => {
    return (
        <Link to={`/contacts/${contact.id}`} className="contact__item">
        <div className="contact__header">
          <div className="contact__image">
            <img 
              src={contact.photoUrl ? contact.photoUrl : 'default-image-url.jpg'} 
              alt={contact.name ? contact.name : "No Name"} 
            />
          </div>
          <div className="contact__details">
            <p className="contact_name">
              {contact.name ? contact.name.substring(0, 15) : "No Name"} 
            </p>
            <p className="contact_title">
              {contact.title ? contact.title : "No Title"}
            </p>
          </div>
        </div>
        <div className="contact__body">
          <p>
            <i className="bi bi-envelope"></i> 
            {contact.email ? contact.email.substring(0, 20) : "No Email"}
          </p>
          <p>
            <i className="bi bi-geo"></i> 
            {contact.address ? contact.address : "No Address"}
          </p>
          <p>
            <i className="bi bi-telephone"></i> 
            {contact.phone ? contact.phone : "No Phone"}
          </p>
          <p>
            {contact.status === 'Active' ? (
              <i className='bi bi-check-circle'></i>
            ) : (
              <i className='bi bi-x-circle'></i>
            )} 
            {contact.status ? contact.status : "Unknown Status"}
          </p>
        </div>
      </Link>
      
    )
  }

export default Contact